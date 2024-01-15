<script setup>
import { ref, computed, watch } from 'vue'
import { useFilterStore } from '@/stores/filterStore'
import FilterCriteria from '@/components/filters/FilterCriteria.vue'
import { updateFilter, createFilter, deleteFilter } from '@/services/apiService'

const store = useFilterStore()

const filterName = ref(store.isNew ? 'New Filter' : store.filterDetails?.filterName || '')
const originalFilterName = ref(store.isNew ? '' : store.filterDetails?.filterName || '')

const filterCriteria = ref([])
const defaultCriteria = computed(() => {
  return {
    comparisonCondition: store.filterCriteriaOptions.comparisonConditions[0],
    criteriaType: store.filterCriteriaOptions.criteriaTypes[0]
  }
})

const addCriteriaRow = () => {
  const newCriteria = {
    criteriaId: Date.now(),
    criteriaType: { ...defaultCriteria.value.criteriaType },
    comparisonCondition: { ...defaultCriteria.value.comparisonCondition },
    criteriaValue: ''
  }
  filterCriteria.value = [...filterCriteria.value, newCriteria]
}

if (store.isNew) {
  addCriteriaRow()
}

watch(
  () => store.filterDetails?.filterName,
  (newName) => {
    if (!store.isNew) {
      filterName.value = newName
      originalFilterName.value = newName
    }
  }
)

const closeModal = () => {
  if (store.isNew) {
    store.setIsNewToFalse()
  }
  store.closeFilterDetails()
}

const handleCriteriaUpdate = (updatedCriteria) => {
  //   const index = filterCriteria.value.findIndex((c) => c.criteriaId === updatedCriteria.criteriaId)
  //   if (index !== -1) {
  //     filterCriteria.value = [
  //       ...filterCriteria.value.slice(0, index),
  //       updatedCriteria,
  //       ...filterCriteria.value.slice(index + 1)
  //     ]
  //   }
  console.log('upCrit', updatedCriteria)
}

const deleteCriteriaRow = (criteriaId) => {
  filterCriteria.value = filterCriteria.value.filter((c) => c.criteriaId !== criteriaId)
}

const saveFilter = async () => {
  const criteriaSource = store.isNew ? filterCriteria.value : store.filterCriteria

  const criteriaData = criteriaSource.map((criteria) => ({
    criteriaType: {
      criteriaTypeId: criteria.criteriaType.criteriaTypeId,
      typeName: criteria.criteriaType.typeName
    },
    comparisonCondition: {
      conditionId: criteria.comparisonCondition.conditionId,
      conditionName: criteria.comparisonCondition.conditionName
    },
    criteriaValue: criteria.criteriaValue
  }))

  const newFilterData = {
    filterName: filterName.value,
    filterCriteria: criteriaData
  }

  if (store.isNew) {
    await createFilter(newFilterData)
    store.setIsNewToFalse()
  } else {
    await updateFilter(store.filterDetails.filterId, newFilterData)
  }
  await store.fetchFilters()
  closeModal()
}

const deleteFilterAndCriteria = async () => {
  await deleteFilter(store.filterDetails.filterId)
  await store.fetchFilters()
  closeModal()
}

watch(
  () => store.filterDetails,
  (newDetails) => {
    // if (!store.isNew && newDetails) {
    filterName.value = newDetails.filterName
    originalFilterName.value = newDetails.filterName
    filterCriteria.value = [...store.filterCriteria]
    // }
  },
  { immediate: true }
)
</script>

<template>
  <div>
    <div v-if="store.isModalModeOn" class="modal-overlay" @click.self="closeModal">
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
        <button v-if="!store.isNew" class="delete-btn" @click="deleteFilterAndCriteria">
          Delete
        </button>
        <button class="close-btn" @click="closeModal">Close</button>
      </div>
    </div>
    <div v-if="!store.isModalModeOn" class="dialog">
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
      <button v-if="!store.isNew" class="delete-btn" @click="deleteFilterAndCriteria">
        Delete
      </button>
      <button class="close-btn" @click="closeModal">Close</button>
    </div>
  </div>
</template>

<style scoped>
.dialog {
  border: 2px solid green;
  width: 100%;
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
.save-btn,
.delete-btn {
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
.delete-btn:hover,
.close-btn:hover {
  background-color: #e2e2ff;
}
</style>
