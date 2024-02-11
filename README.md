
# Auction API

<p>Welcome to the Auction API documentation. This API allows you to manage auctions, place bids, and perform authentication operations.</p>

# Technology Stack
<ul>
    <li>Java 21</li>
    <li>Spring Boot, Spring Data, Spring Security, Spring MVC.</li>
    <li>Hibernate</li>
    <li>PostgreSQL</li>
    <li>Swagger</li>
    <li>Gradle</li>
    <li>Docker</li>
    <li>Jackson</li>
  </ul>


# Auction API Documentation

<h2>auction-controller</h2>

<h3>PUT /api/v1/auctions/edit</h3>
<p>Edit auction</p>

<h3>POST /api/v1/auctions/create</h3>
<p>Create auction</p>

<h3>GET /api/v1/auctions/start-price</h3>
<p>Get auction by start price</p>

<h3>GET /api/v1/auctions/name</h3>
<p>Get auction by name</p>

<h3>GET /api/v1/auctions/get-by-id</h3>
<p>Get auction by id</p>

<h3>GET /api/v1/auctions/bids</h3>
<p>Get bids history</p>

<h3>GET /api/v1/auctions/all</h3>
<p>Get all auctions</p>

<h2>bid-controller</h2>

<h3>POST /api/v1/bids/place</h3>
<p>Place bid</p>

<h3>GET /api/v1/bids</h3>
<p>Get all bids</p>

<h3>DELETE /api/v1/bids</h3>
<p>Delete bid</p>

<h3>GET /api/v1/bids/max</h3>
<p>Get max bid</p>

<h2>auth-controller</h2>

<h3>POST /api/v1/auth/sign-up</h3>
<p>Sign up user</p>

<h3>POST /api/v1/auth/login</h3>
<p>Login user</p>

</body>
</html>

# License
This project is licensed under the MIT License.
