<script setup>
import { ref, onMounted } from 'vue'
import { useFilterStore } from '@/stores/filterStore'
import FilterItem from '@/components/filters/FilterItem.vue'

const filterStore = useFilterStore()
const isNewFilterModalOpen = ref(false)

const openNewFilterModal = () => {
  isNewFilterModalOpen.value = true
}

onMounted(async () => {
  await filterStore.fetchFilters()
  await filterStore.fetchCriteriaTypes()
  await filterStore.fetchComparisonConditions()
})
</script>

<template>
  <div>
    <h1>Filters</h1>
    <FilterItem v-if="isNewFilterModalOpen" :isNew="true" @close="isNewFilterModalOpen = false" />
    <div v-for="filter in filterStore.filters" :key="filter.filterId">
      <FilterItem :filter="filter" />
    </div>
    <button class="add-new-btn" @click="openNewFilterModal">Add</button>
  </div>
</template>

<style scoped>
.add-new-btn {
  padding: 2px 6px;
  border: 1px solid #f5f5f5;
  background-color: #f5f5f5;
  border-radius: 6px;
  cursor: pointer;
  font-size: 18px;
}

.add-new-btn:hover {
  background-color: #e2e2ff;
}
</style>
