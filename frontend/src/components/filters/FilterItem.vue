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
</script>

<template>
  <div class="filter-item" @click="openModal">
    <h3>{{ filter.filterName }}</h3>
  </div>

  <Teleport to="body">
    <div v-if="showModal" class="modal-overlay" @click="showModal = false">
      <div class="modal-content" @click.stop>
        <h4>Filter Criteria</h4>
        <ul>
          <li v-for="criteria in filterCriteria" :key="criteria.criteriaId">
            {{ criteria.criteriaValue }}
          </li>
        </ul>
        <button @click="showModal = false" class="close-btn">Close</button>
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
  border: none;
  background: none;
  cursor: pointer;
  font-size: 24px;
}
</style>
