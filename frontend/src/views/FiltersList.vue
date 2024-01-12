<script setup>
import { onMounted } from 'vue'
import { useFilterStore } from '@/stores/filterStore'
import FilterItem from '@/components/filters/FilterItem.vue'

const filterStore = useFilterStore()

const fetchFilters = async () => {
  await filterStore.fetchFilters()
  await filterStore.fetchCriteriaTypes()
  await filterStore.fetchComparisonConditions()
}

onMounted(fetchFilters)
</script>

<template>
  <div>
    <h1>Filters</h1>
    <FilterItem v-for="filter in filterStore.filters" :key="filter.filterId" :filter="filter" />
  </div>
</template>
