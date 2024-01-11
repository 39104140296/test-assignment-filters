import { defineStore } from 'pinia'
import { findAllCriteriaTypes, findAllComparisonConditions } from '@/services/apiService.js'

export const useFilterStore = defineStore('filter', {
  state: () => ({
    criteriaTypes: null,
    comparisonConditions: null
  }),
  actions: {
    async fetchCriteriaTypes() {
      if (this.criteriaTypes === null) {
        this.criteriaTypes = await findAllCriteriaTypes()
      }
    },
    async fetchComparisonConditions() {
      if (this.comparisonConditions === null) {
        this.comparisonConditions = await findAllComparisonConditions()
      }
    }
  }
})
