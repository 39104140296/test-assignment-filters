<script setup>
import { defineProps } from 'vue'

defineProps({
  criteria: Object
})
</script>

<template>
  <div>
    <div>{{ criteria.criteriaType.typeName }}</div>
    <div>{{ criteria.comparisonCondition.conditionName }}</div>
    <div>{{ criteria.value }}</div>
  </div>
</template>

<style scoped></style>
