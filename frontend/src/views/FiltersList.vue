<script setup>
import { onMounted, ref } from 'vue'
import { getAllFilters } from '@/services/apiService'
import FilterItem from '@/components/filters/FilterItem.vue'

const filters = ref([])

const fetchFilters = async () => {
  const data = await getAllFilters()
  data ? (filters.value = data) : console.log('Filters cannot be retrieved')
}

onMounted(fetchFilters)
</script>

<template>
  <div>
    <h1>Filters</h1>
    <FilterItem v-for="filter in filters" :key="filter.filterId" :filter="filter" />
  </div>
</template>
