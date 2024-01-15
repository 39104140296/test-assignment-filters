<script setup>
import { onMounted } from 'vue'
import { useFilterStore } from '@/stores/filterStore'
import FilterItem from '@/components/filters/FilterItem.vue'
import FilterDetails from '@/components/filters/FilterDetails.vue'

const store = useFilterStore()

onMounted(async () => {
  await store.fetchFilters()
  await store.fetchFilterCriteriaOptions()
})
</script>

<template>
  <div>
    <FilterDetails v-if="store.isFilterDetailsOpen && !store.isModalModeOn" />
    <h1>Filters</h1>
    <div class="filters">
      <FilterItem v-for="filter in store.filters" :key="filter.filterId" :filter="filter" />
    </div>
    <div class="buttons">
      <button class="add-btn" @click="store.openNewFilterDetails">Add</button>
      <button class="mode-btn" v-if="!store.isModalModeOn" @click="store.toggleModalMode">
        Modal
      </button>
      <button class="mode-btn" v-if="store.isModalModeOn" @click="store.toggleModalMode">
        Dialog
      </button>
    </div>
  </div>
</template>

<style scoped>
.filters {
  display: grid;
  grid-template-columns: repeat(6, 1fr);
  gap: 8px;
  margin-bottom: 16px;
}
.buttons {
  display: flex;
  justify-content: space-between;
}

.add-btn,
.mode-btn {
  padding: 2px 6px;
  border: 1px solid #f5f5f5;
  background-color: #f5f5f5;
  border-radius: 6px;
  cursor: pointer;
  font-size: 18px;
}

.add-btn:hover,
.mode-btn:hover {
  background-color: #e2e2ff;
}
</style>
