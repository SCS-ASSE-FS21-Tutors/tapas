In this directory are some files to mock an auction house to test WebSub local.

To run a local WebSubHub instance

1. Start a mongodb in docker:

-   docker run -d -p 27017:27017 -p 28017:28017 -e AUTH=no tutum/mongodb

2. Install a local hub

-   yarn global add websub-hub

3. Run the hub localy

-   websub-hub -l info -m mongodb://localhost:27017/hub

Create an example subscription

-   node auction-house/subscriber.js

Create an example auctionhouse

-   node auction-house/auctions.js

Publish to the hub

-   node auction-house/publisher.js

Mostly inspired by: https://github.com/hemerajs/websub-hub
