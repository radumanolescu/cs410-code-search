const BASE_URL = 'http://localhost:8081'

class CodeSearchClient {
  constructor (baseUrl) {
    this.baseUrl = baseUrl
  }

  async search (query) {
    console.log(`Making rest call to get results ${query}`)
    const response = await fetch(`${this.baseUrl}/search?query=${encodeURI(query)}`)
    const results = await response.json()
    return results
  }
}

const client = new CodeSearchClient(BASE_URL)
export default client;