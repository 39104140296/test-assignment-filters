<script setup>
import { ref } from 'vue'
import AddFilter from '@/components/filters/AddFilter.vue'
import FiltersList from '@/components/filters/FiltersList.vue'

const filters = ref([])
const showAddFilter = ref(false)

const addFilter = (newFilter) => {
  filters.value.push(newFilter)
  showAddFilter.value = false
}

const deleteFilter = (filterId) => {
  filters.value = filters.value.filter((f) => f.filterId !== filterId)
}

const editFilter = (filterId) => {}
</script>

<template>
  <div class="home-page">
    <button @click="showAddFilter = !showAddFilter">
      {{ showAddFilter ? 'Show Filters List' : 'Add Filter' }}
    </button>

    <div v-if="showAddFilter">
      <AddFilter @filter-added="addFilter" />
    </div>

    <div v-else>
      <FiltersList :filters="filters" @delete-filter="deleteFilter" @edit-filter="editFilter" />
    </div>
  </div>
</template>

<style scoped>
.home-page {
}
</style>
