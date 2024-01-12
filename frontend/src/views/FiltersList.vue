<script setup>
import { onMounted, ref } from 'vue'
import { getAllFilters } from '@/services/apiService'
import { useFilterStore } from '@/stores/filterStore'
import FilterItem from '@/components/filters/FilterItem.vue'

const filterStore = useFilterStore()
const filters = ref([])

const fetchFilters = async () => {
  const filterData = await getAllFilters()
  filters.value = filterData

  await filterStore.fetchCriteriaTypes()
  await filterStore.fetchComparisonConditions()
}

onMounted(fetchFilters)
</script>

<template>
  <div>
    <h1>Filters</h1>
    <FilterItem v-for="filter in filters" :key="filter.filterId" :filter="filter" />
  </div>
</template>
