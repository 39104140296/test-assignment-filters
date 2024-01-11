import axios from 'axios'

const axiosInstance = axios.create({
  baseURL: 'http://localhost:8080/api/filters'
})

export const getAllFilters = async () => {
  try {
    const response = await axiosInstance.get('/')
    return response.data
  } catch (error) {
    console.error('Error getting all filters: ', error)
    throw error
  }
}

export const getFilterCriteria = async (filterId) => {
  try {
    const response = await axiosInstance.get(`/${filterId}/criteria`)
    return response.data
  } catch (error) {
    console.error(`Error getting criteria for filter ${filterId}: `, error)
    throw error
  }
}

export const updateFilterName = async (filterId, newFilterName) => {
  try {
    const response = await axiosInstance.put(`/${filterId}/name`, { filterName: newFilterName })
    return response.data
  } catch (error) {
    console.error('Error updating filter name: ', error)
    throw error
  }
}

export const updateFilterCriteria = async (filterId, criteriaDTOList) => {
  try {
    const response = await axiosInstance.put(`/${filterId}/criteria`, criteriaDTOList)
    return response.data
  } catch (error) {
    console.error('Error updating filter criteria: ', error)
    throw error
  }
}

export const createFilter = async (createFilterDTO) => {
  try {
    const response = await axiosInstance.post('/', createFilterDTO)
    return response.data
  } catch (error) {
    console.error('Error creating new filter: ', error)
    throw error
  }
}

export const deleteFilter = async (filterId) => {
  try {
    const response = await axiosInstance.delete(`/${filterId}`)
    return response.data
  } catch (error) {
    console.error('Error deleting filter: ', error)
    throw error
  }
}

export const findAllCriteriaTypes = async () => {
  try {
    const response = await axiosInstance.get('/criteria-types')
    return response.data
  } catch (error) {
    console.error('Error fetching criteria types: ', error)
    throw error
  }
}

export const findAllComparisonConditions = async () => {
  try {
    const response = await axiosInstance.get('/comparison-conditions')
    return response.data
  } catch (error) {
    console.error('Error fetching comparison conditions: ', error)
    throw error
  }
}
