<script setup>
import { ref } from 'vue'
import FilterRow from './FilterRow.vue'

const filterRows = ref([{ id: 1 }])
let nextId = 2

const addRow = () => {
  filterRows.value.push({ id: nextId++ })
}

const deleteRow = (id) => {
  filterRows.value = filterRows.value.filter((row) => row.id !== id)
}
</script>

<template>
  <div class="add-filter">
    <div v-for="row in filterRows" :key="row.id">
      <FilterRow :filterId="row.id" @deleteRow="deleteRow" />
    </div>
    <button @click="addRow">Add Row</button>
  </div>
</template>

<style scoped>
.add-filter {
}
</style>
