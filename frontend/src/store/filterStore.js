import { defineStore } from 'pinia'
import { getAllFilters, getFilterOptions, getFilterCriteria } from '@/services/apiService.js'

const localStorageKey = 'modalMode'

export const useFilterStore = defineStore('filter', {
  state: () => {
    const isModalModeOn = localStorage.getItem(localStorageKey)
      ? JSON.parse(localStorage.getItem(localStorageKey))
      : false
    return {
      filters: [],
      filterCriteria: [],
      filterCriteriaOptions: {},
      isFilterDetailsOpen: false,
      filterDetails: {},
      isModalModeOn,
      isNew: false
    }
  },
  actions: {
    async fetchFilters() {
      this.filters = await getAllFilters()
    },
    async fetchFilterCriteriaOptions() {
      this.filterCriteriaOptions = await getFilterOptions()
    },
    async fetchFilterCriteria() {
      this.filterCriteria = await getFilterCriteria(this.filterDetails.filterId)
    },
    updateCriteria(criteriaId, updatedCriteriaData) {
      const index = this.filterCriteria.findIndex((c) => c.criteriaId === criteriaId)
      if (index !== -1) {
        this.filterCriteria[index] = { ...this.filterCriteria[index], ...updatedCriteriaData }
      }
    },
    async openFilterDetails(filter) {
      if (this.isNew) {
        this.isNew = false
      }
      this.filterDetails = filter
      await this.fetchFilterCriteria()
      this.isFilterDetailsOpen = true
    },
    openNewFilterDetails() {
      this.isNew = true
      this.isFilterDetailsOpen = true
    },
    closeFilterDetails() {
      this.isFilterDetailsOpen = false
    },
    toggleModalMode() {
      this.isModalModeOn = !this.isModalModeOn
      localStorage.setItem(localStorageKey, JSON.stringify(this.isModalModeOn))
    },
    setIsNewToFalse() {
      this.isNew = false
    }
  }
})
