<script setup>
import { ref } from 'vue'
import FilterCriteria from './FilterCriteria.vue'

defineProps({
  filter: Object
})
defineEmits(['update-filter'])
const isModalOpen = ref(false)
const newCriteriaList = ref([])

function closeModal() {
  newCriteriaList.value = []
  isModalOpen.value = false
}

function addCriteriaRow() {
  newCriteriaList.value.push({
    criteriaId: Date.now(),
    criteriaType: { criteriaTypeId: 1, typeName: 'Amount', dataType: 'NUMBER' },
    comparisonCondition: { conditionId: 1, conditionName: 'Equal' },
    value: ''
  })
}
</script>

<template>
  <div>
    <div class="filter-item" @click="isModalOpen = true">
      <span>{{ filter.filterName }}</span>
    </div>

    <teleport to="body">
      <div v-if="isModalOpen" class="modal-overlay" @click.self="closeModal">
        <div class="modal">
          <h3>Edit Filter: {{ filter.filterName }}</h3>
          <ul>
            <li v-for="criteria in filter.filterCriteriaList" :key="criteria.criteriaId">
              <FilterCriteria :criteria="criteria" />
            </li>
            <li v-for="(criteria, index) in newCriteriaList" :key="index">
              <FilterCriteria :criteria="criteria" mode="new" />
            </li>
          </ul>
          <button @click="addCriteriaRow">+ Add Row</button>
          <button class="close-modal" @click="closeModal">Close</button>
        </div>
      </div>
    </teleport>
  </div>
</template>

<style scoped>
.filter-item {
  cursor: pointer;
}

.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  background-color: rgba(0, 0, 0, 0.6);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1000;
}

.modal {
  background: white;
  border-radius: 8px;
  max-width: 600px;
  width: 90%;
  padding: 20px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  z-index: 1001;
}

.close-modal {
  position: absolute;
  top: 15px;
  right: 15px;
  border: none;
  background: none;
  cursor: pointer;
}

ul {
  list-style: none;
  padding: 0;
  margin: 0;
}

li {
  margin-bottom: 15px;
}

button {
  cursor: pointer;
  padding: 0.5rem 1rem;
  margin-top: 0.5rem;
  border: 1px solid #ccc;
  border-radius: 4px;
  background-color: #fff;
  transition: background-color 0.2s;
}

button:hover {
  background-color: #eef;
}
</style>
