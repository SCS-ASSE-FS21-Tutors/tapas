// Require the framework and instantiate it
const fastify = require('fastify')({ logger: true })

// Declare a route
fastify.get('/auctions', async (request, reply) => {
  console.log('content provided')

  return [
    {
      id: '2',
      content_text: 'This is a second item.',
      url: 'https://example.org/second-item'
    },
    {
      id: '1',
      content_html: '<p>Hello, world!</p>',
      url: 'https://example.org/initial-post'
    }
  ]
})

fastify.get('/websub', async (request, reply) => {
  console.log('content provided')

  return {
    topic: 'http://localhost:3100/auctions'
  }
})

// Run the server!
const start = async () => {
  try {
    await fastify.listen(3100)
  } catch (err) {
    fastify.log.error(err)
    process.exit(1)
  }
}
start()
