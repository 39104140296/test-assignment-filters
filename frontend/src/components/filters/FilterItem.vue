<script setup>
import { ref, computed } from 'vue'
import { useFilterStore } from '@/stores/filterStore'
import FilterCriteria from '@/components/filters/FilterCriteria.vue'

const props = defineProps({
  filter: Object
})

const filterStore = useFilterStore()
const showModal = ref(false)
const filterCriteria = ref([])
const defaultCriteria = computed(() => {
  return filterStore.comparisonConditions[0]
})

const openModal = async () => {
  await filterStore.fetchFilterCriteria(props.filter.filterId)
  filterCriteria.value = [...filterStore.filterCriteria]
  showModal.value = true
}

const handleCriteriaUpdate = (criteriaId, updatedCriteria) => {
  filterStore.updateCriteria(criteriaId, updatedCriteria)
}

const addCriteriaRow = () => {
  const newCriteria = {
    criteriaId: Date.now(),
    criteriaType: {
      criteriaTypeId: defaultCriteria.value.criteriaType.criteriaTypeId,
      typeName: defaultCriteria.value.criteriaType.dataType,
      dataType: defaultCriteria.value.criteriaType.typeName
    },
    comparisonCondition: defaultCriteria,
    criteriaValue: ''
  }
  filterCriteria.value.push(newCriteria)
}
</script>

<template>
  <div class="filter-item" @click="openModal">
    <h3>{{ filter.filterName }}</h3>
  </div>

  <Teleport to="body">
    <div v-if="showModal" class="modal-overlay" @click.self="showModal = false">
      <div class="modal-content" @click.stop>
        <h3>{{ filter.filterName }}</h3>
        <div v-for="criteria in filterCriteria" :key="criteria.criteriaId">
          <FilterCriteria :criteria="criteria" @update:criteria="handleCriteriaUpdate" />
        </div>
        <button @click="addCriteriaRow">Add Row</button>
        <button class="close-btn" @click="showModal = false">Close</button>
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
  max-width: 450px;
  z-index: 1001;
  position: relative;
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

.close-btn:hover {
  background-color: #e2e2ff;
}
</style>
