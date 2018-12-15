const BASE_URL = 'http://localhost:8081'

class CodeSearchClient {
  constructor (baseUrl) {
    this.baseUrl = baseUrl
  }

  async search (query) {
    console.log(`Making rest call to get results ${query}`)
    const response = await fetch(`${this.baseUrl}/search?query=${encodeURI(query)}`)
    const json = await response.json()
    console.log(json)
    return json
  }
  async codesearch (query) {
    console.log(`Making rest call to get results weighted for language keywords ${query}`)
    const response = await fetch(`${this.baseUrl}/codesearch?query=${encodeURI(query)}`)
    const json = await response.json()
    console.log(json)
    return json
  }

  async ping () {
    try {
      const response = await fetch(`${this.baseUrl}/ping`)
      if (response.status !== 200) {
        return {
          'status': 'NOTREADY',
          'reason': await response.text()
        }
      }
      const json = await response.json()
      return json
    } catch (e) {
      console.log(e)
      return {
        'status': 'NOTREADY',
        'reason': e.toString()
      }
    }
  }
}

const client = new CodeSearchClient(BASE_URL)
export default client;