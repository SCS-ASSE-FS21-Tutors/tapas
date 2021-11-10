const axios = require('axios').default

// Run the server!
const start = async () => {
  await axios
    .post('http://localhost:3000/publish', {
      'hub.mode': 'publish',
      'hub.url': 'http://localhost:3100/auctions'
    })
    .then(response => {
      console.log(response.data)
    })
    .catch(error => {
      console.log(error)
    })
}
start()
