<script setup>
import { ref, computed, watch } from 'vue'
import { useFilterStore } from '@/store/filterStore'
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

watch(
  () => store.filterDetails.filterId,
  (newFilterId, oldFilterId) => {
    if (newFilterId !== oldFilterId) {
      loadFilterCriteria()
    }
  }
)

watch(
  () => store.filterDetails?.filterName,
  (newName) => {
    if (!store.isNew) {
      filterName.value = newName
      originalFilterName.value = newName
    }
  }
)

watch(
  () => store.filterDetails,
  (newDetails) => {
    if (!store.isNew && newDetails) {
      filterName.value = newDetails.filterName
      originalFilterName.value = newDetails.filterName
      filterCriteria.value = [...store.filterCriteria]
    }
  },
  { immediate: true, deep: true }
)

const loadFilterCriteria = async () => {
  if (!store.isNew) {
    await store.fetchFilterCriteria()
    filterCriteria.value = [...store.filterCriteria]
  } else {
    filterCriteria.value = []
    addCriteriaRow()
  }
}

const handleCriteriaUpdate = (updatedCriteria) => {
  const index = filterCriteria.value.findIndex((c) => c.criteriaId === updatedCriteria.criteriaId)
  if (index !== -1) {
    filterCriteria.value.splice(index, 1, updatedCriteria)
  }
}

const addCriteriaRow = () => {
  const newCriteria = { ...defaultCriteria.value, criteriaId: Date.now().toString() }
  filterCriteria.value.push(newCriteria)
}

if (store.isNew) {
  addCriteriaRow()
}

const deleteCriteriaRow = (criteriaId) => {
  filterCriteria.value = filterCriteria.value.filter((c) => c.criteriaId !== criteriaId)
}

const saveFilter = async () => {
  const hasEmptyCriteriaValue = filterCriteria.value.some((criteria) => {
    if (typeof criteria.criteriaValue === 'number') {
      return isNaN(criteria.criteriaValue)
    } else if (typeof criteria.criteriaValue === 'string') {
      return criteria.criteriaValue.trim() === ''
    } else {
      return true
    }
  })

  if (hasEmptyCriteriaValue) {
    alert('Please fill in all criteria values before saving.')
    return
  }

  const criteriaData = filterCriteria.value.map((criteria) => ({
    criteriaType: {
      criteriaTypeId: criteria.criteriaType.criteriaTypeId,
      typeName: criteria.criteriaType.typeName
    },
    comparisonCondition: {
      conditionId: criteria.comparisonCondition.conditionId,
      conditionName: criteria.comparisonCondition.conditionName
    },
    criteriaValue: String(criteria.criteriaValue).trim().replace(/\s+/g, ' ')
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

const closeModal = () => {
  if (store.isNew) {
    store.setIsNewToFalse()
  }
  store.closeFilterDetails()
}
</script>

<template>
  <div
    :class="{ 'modal-overlay': store.isModalModeOn }"
    @click.self="store.isModalModeOn ? closeModal() : null"
  >
    <div :class="{ modal: store.isModalModeOn, dialog: !store.isModalModeOn }">
      <div class="filter-header">
        <h3>{{ filterName }}</h3>
        <button class="close-button" @click="closeModal">&times;</button>
      </div>
      <div class="filter-body">
        <div class="filter-name">
          <h4>Filter name</h4>
          <input v-model="filterName" class="filter-name-input" />
        </div>
        <div class="criteria-box">
          <h4>Criteria</h4>
          <div class="criteria-container">
            <FilterCriteria
              v-for="criteria in filterCriteria"
              :key="criteria.criteriaId"
              :criteria="criteria"
              :showDeleteButton="filterCriteria.length > 1"
              @update:criteria="handleCriteriaUpdate"
              @delete:criteria="deleteCriteriaRow"
            />
          </div>
        </div>
        <button class="add-row-btn" @click="addCriteriaRow">+ Add Row</button>
      </div>
      <div class="filter-footer">
        <button class="save-btn" @click="saveFilter">Save</button>
        <button v-if="!store.isNew" class="delete-btn" @click="deleteFilterAndCriteria">
          Delete
        </button>
      </div>
    </div>
  </div>
</template>

<style scoped>
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
.dialog,
.modal {
  display: grid;
  grid-template-rows: auto 1fr auto;
  width: 48rem;
  background: #fff;
  border: 1px solid rgb(193, 197, 201);
  border-radius: 8px;
  min-height: 20rem;
  max-height: 40rem;
  resize: vertical;
  overflow: auto;
}

.modal {
  border: none;
}

.filter-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  color: #fff;
  height: 36px;
  border: 1px solid rgb(34, 153, 238);
  background-color: rgb(34, 153, 238);
}

.close-button {
  height: 36px;
  width: 36px;
  color: #fff;
  background-color: rgb(34, 153, 238);
  border: none;
  font-size: 20px;
  cursor: pointer;
}

.filter-body {
  display: flex;
  flex-direction: column;
  margin-top: 20px;
}

.filter-name,
.criteria-box {
  display: flex;
  gap: 20px;
  align-items: flex-start;
  margin-bottom: 20px;
}

.filter-name input {
  padding: 4px;
  border: 1px solid #ccc;
  border-radius: 4px;
}

h3,
h4 {
  width: 100px;
  padding-left: 10px;
  margin: 0;
}

h3 {
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  width: 30rem;
}

.criteria-container {
  display: flex;
  flex-direction: column;
}

.add-row-btn,
.save-btn,
.delete-btn {
  background-color: #6c6d6d;
  color: #fff;
  width: 90px;
  height: 24px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.add-row-btn {
  margin: 10px auto 0 auto;
}

.add-row-btn:hover,
.save-btn:hover {
  background: rgb(34, 153, 238);
}

.delete-btn:hover {
  background-color: #f13a3a;
}

.filter-footer {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 10px;
  height: 36px;
  margin-top: 30px;
  background-color: rgb(193, 197, 201);
}
</style>
