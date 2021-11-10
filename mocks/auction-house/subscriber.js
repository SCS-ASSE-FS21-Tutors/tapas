// Require the framework and instantiate it
const fastify = require('fastify')({ logger: true })
const axios = require('axios').default

// Declare a route
fastify.get('/auction-created', async (request, reply) => {
  console.log('subscription verified', request.query)
  console.log(request.query)
  return request.query
})

fastify.post('/auction-created', async (request, reply) => {
  console.log('received blog content', request.body)
  reply.send()
})

// Run the server!
const start = async () => {
  // subscribe to the feed

  try {
    await fastify.listen(3200)
  } catch (err) {
    fastify.log.error(err)
    process.exit(1)
  }

  await axios
    .post('http://localhost:3000', {
      'hub.callback': 'http://localhost:3200/auction-created',
      'hub.mode': 'subscribe',
      'hub.topic': 'http://localhost:3100/auctions',
      'hub.ws': false
    })
    .then(response => {
      console.log(response.data)
    })
    .catch(error => {
      console.log(error)
    })
}
start()
