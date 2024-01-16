<script setup>
import { onMounted } from 'vue'
import { useFilterStore } from '@/store/filterStore'
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
    <div class="filters">
      <FilterItem v-for="filter in store.filters" :key="filter.filterId" :filter="filter" />
    </div>
    <div class="buttons">
      <button class="add-btn" @click="store.openNewFilterDetails">+ Add Filter</button>
      <button class="mode-btn" @click="store.toggleModalMode">
        <div v-if="store.isModalModeOn">Dialog Mode</div>
        <div v-if="!store.isModalModeOn">Modal Mode</div>
      </button>
    </div>
  </div>
</template>

<style scoped>
.filters {
  width: 48rem;
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 8px;
  padding: 16px 0;
}
.buttons {
  display: flex;
  justify-content: space-between;
}

.add-btn,
.mode-btn {
  display: flex;
  justify-content: center;
  align-items: center;
  border: none;
  border-radius: 4px;
  color: #fff;
  cursor: pointer;
  background-color: #6c6d6d;
  width: 90px;
  height: 24px;
  transition: background-color 0.2s;
}

.add-btn:hover,
.mode-btn:hover {
  background-color: rgb(34, 153, 238);
}
</style>
