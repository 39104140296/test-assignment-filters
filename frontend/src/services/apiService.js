const API_BASE_URL = 'http://localhost:8080/api'

const handleResponse = async (response) => {
  if (!response.ok) {
    const error = await response.text()
    throw new Error(error)
  }
  return response.json()
}

export const getAllFilters = async () => {
  try {
    const response = await fetch(`${API_BASE_URL}/filters`)
    return await handleResponse(response)
  } catch (error) {
    console.error('Error fetching filters:', error)
    throw error
  }
}
