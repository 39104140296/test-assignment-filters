<script setup>
import { onMounted, ref } from 'vue'
import { getAllFiltersWithCriteria } from '@/services/apiService'
import FiltersList from '@/components/filters/FiltersList.vue'

const filters = ref([])

onMounted(async () => {
  try {
    filters.value = await getAllFiltersWithCriteria()
  } catch (error) {
    console.error('Failed to load filters:', error)
  }
})
</script>

<template>
  <div class="home-page">
    <div>
      <FiltersList :filters="filters" />
    </div>
  </div>
</template>

<style scoped>
.home-page {
}
</style>
