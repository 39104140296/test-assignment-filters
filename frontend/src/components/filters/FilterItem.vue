<script setup>
import { ref } from 'vue'
import { useFilterStore } from '@/stores/filterStore'
import { getFilterCriteria } from '@/services/apiService'

const filterStore = useFilterStore()

const props = defineProps({ filter: Object })
const showModal = ref(false)
const filterCriteria = ref([])

const openModal = async () => {
  await filterStore.fetchCriteriaTypes()
  await filterStore.fetchComparisonConditions()
  filterCriteria.value = await getFilterCriteria(props.filter.filterId)
  showModal.value = true
}

const getFilteredConditions = (criteriaTypeId) => {
  return filterStore.comparisonConditions.filter((condition) => {
    return condition.criteriaType.criteriaTypeId === criteriaTypeId
  })
}
</script>

<template>
  <div class="filter-item" @click="openModal">
    <h3>{{ filter.filterName }}</h3>
  </div>

  <Teleport to="body">
    <div v-if="showModal" class="modal-overlay" @click="showModal = false">
      <div class="modal-content" @click.stop>
        <h3>{{ filter.filterName }}</h3>
        <ul>
          <li v-for="criteria in filterCriteria" :key="criteria.criteriaId" class="criteria-row">
            <select v-model="criteria.criteriaType.criteriaTypeId">
              <option
                v-for="type in filterStore.criteriaTypes"
                :key="type.criteriaTypeId"
                :value="type.criteriaTypeId"
              >
                {{ type.typeName }}
              </option>
            </select>

            <select v-model="criteria.comparisonCondition.conditionId">
              <option
                v-for="condition in getFilteredConditions(criteria.criteriaType.criteriaTypeId)"
                :key="condition.conditionId"
                :value="condition.conditionId"
              >
                {{ condition.conditionName }}
              </option>
            </select>

            <input v-model="criteria.criteriaValue" placeholder="Enter criteria value" />
          </li>
        </ul>
        <button @click="showModal = false" class="close-btn">CLOSE</button>
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
  border-radius: 6px;
  background: none;
  cursor: pointer;
  font-size: 14px;
}

.close-btn:hover {
  background-color: #f5f5f5;
}

.criteria-row {
  display: flex;
  gap: 8px;
  padding: 8px;
  align-items: center;
}

.criteria-row select,
.criteria-row input {
  padding: 4px;
  border: 1px solid #ccc;
  border-radius: 4px;
}
</style>
