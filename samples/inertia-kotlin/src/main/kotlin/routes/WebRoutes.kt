package routes

import io.konaframework.http.Router
import io.konaframework.inertia.Inertia

object WebRoutes {
    fun register(router: Router) {
        router.get("/") {
            Inertia.render(
                "Pages/Welcome",
                mapOf("message" to "Welcome to Kona Inertia"),
            )
        }
        router.get("/about") {
            Inertia.render("Pages/About", mapOf("title" to "About"))
        }
        router.get("/leave") {
            Inertia.location("https://example.com")
        }
    }
}
