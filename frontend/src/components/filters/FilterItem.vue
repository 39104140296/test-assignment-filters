<script setup>
import { ref, computed, watch } from 'vue'
import { useFilterStore } from '@/stores/filterStore'
import FilterCriteria from '@/components/filters/FilterCriteria.vue'
import { updateFilterCriteria, updateFilterName, createFilter } from '@/services/apiService'

const props = defineProps({
  filter: Object,
  isNew: Boolean
})

const filterStore = useFilterStore()
const showModal = ref(false)

const filterName = ref(props.isNew ? 'New Filter' : props.filter?.filterName || '')
const originalFilterName = ref(props.isNew ? '' : props.filter?.filterName || '')

const filterCriteria = ref([])
const defaultCriteria = computed(() => filterStore.comparisonConditions[0])

const emit = defineEmits(['close'])

const addCriteriaRow = () => {
  const newCriteria = {
    criteriaId: Date.now(),
    criteriaType: defaultCriteria.value.criteriaType,
    comparisonCondition: defaultCriteria.value,
    criteriaValue: ''
  }
  filterCriteria.value.push(newCriteria)
}

if (props.isNew) {
  showModal.value = true
  addCriteriaRow()
}

watch(
  () => props.filter?.filterName,
  (newName) => {
    if (!props.isNew) {
      filterName.value = newName
      originalFilterName.value = newName
    }
  }
)

const openModal = async () => {
  if (!props.isNew && props.filter) {
    filterName.value = props.filter.filterName
    originalFilterName.value = props.filter.filterName
    await filterStore.fetchFilterCriteria(props.filter.filterId)
    filterCriteria.value = [...filterStore.filterCriteria]
  }
  showModal.value = true
}

const closeModal = () => {
  showModal.value = false
  emit('close')
}

const handleCriteriaUpdate = (updatedCriteria) => {
  const index = filterCriteria.value.findIndex((c) => c.criteriaId === updatedCriteria.criteriaId)
  if (index !== -1) {
    filterCriteria.value[index] = updatedCriteria
  }
}

const deleteCriteriaRow = (criteriaId) => {
  filterCriteria.value = filterCriteria.value.filter((c) => c.criteriaId !== criteriaId)
}

const saveFilter = async () => {
  const criteriaDTOList = filterCriteria.value.map((criteria) => ({
    criteriaTypeId: criteria.criteriaType.criteriaTypeId,
    conditionId: criteria.comparisonCondition.conditionId,
    criteriaValue: criteria.criteriaValue
  }))

  if (props.isNew) {
    const createFilterDTO = {
      filterName: filterName.value,
      criteria: criteriaDTOList
    }
    await createFilter(createFilterDTO)
  } else {
    await updateFilterCriteria(props.filter.filterId, criteriaDTOList)
    if (filterName.value !== originalFilterName.value) {
      await updateFilterName(props.filter.filterId, filterName.value)
    }
  }

  await filterStore.fetchFilters()
  closeModal()
}
</script>

<template>
  <div class="filter-item" @click="openModal">
    <h3>{{ props.isNew ? filterName : filter.filterName }}</h3>
  </div>

  <Teleport to="body">
    <div v-if="showModal" class="modal-overlay" @click.self="closeModal">
      <div class="modal-content" @click.stop>
        <input v-model="filterName" class="filter-name-input" />
        <div v-for="criteria in filterCriteria" :key="criteria.criteriaId">
          <FilterCriteria
            :criteria="criteria"
            :showDeleteButton="filterCriteria.length > 1"
            @update:criteria="handleCriteriaUpdate"
            @delete:criteria="deleteCriteriaRow"
          />
        </div>
        <button class="add-row-btn" @click="addCriteriaRow">Add Row</button>
        <button class="save-btn" @click="saveFilter">Save</button>
        <button class="close-btn" @click="closeModal">Close</button>
      </div>
    </div>
  </Teleport>
</template>

<style scoped>
.filter-item {
  cursor: pointer;
  padding: 16px;
  margin-bottom: 8px;
  background-color: #f5f5f5;
  border: 1px solid #e1e1e1;
  border-radius: 6px;
  transition: background-color 0.2s;
}

.filter-item:hover {
  background-color: #e2e2ff;
}

.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: rgba(0, 0, 0, 0.6);
  z-index: 1000;
}

.modal-content {
  background: white;
  padding: 24px;
  border-radius: 8px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  width: 90%;
  width: 600px;
  z-index: 1001;
  position: relative;
}

.add-row-btn,
.save-btn {
  margin: 2px;
  padding: 2px 6px;
  border: 1px solid #f5f5f5;
  background-color: #f5f5f5;
  border-radius: 6px;
  cursor: pointer;
  font-size: 14px;
}
.close-btn {
  position: absolute;
  top: 10px;
  right: 10px;
  padding: 2px 6px;
  border: 1px solid #f5f5f5;
  background-color: #f5f5f5;
  border-radius: 6px;
  cursor: pointer;
  font-size: 14px;
}

.add-row-btn:hover,
.save-btn:hover,
.close-btn:hover {
  background-color: #e2e2ff;
}
</style>
