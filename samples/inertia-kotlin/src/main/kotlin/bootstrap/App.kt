package bootstrap

import app.http.middleware.HandleInertia
import io.konaframework.core.Application
import io.konaframework.core.Env
import io.konaframework.core.Kona
import io.konaframework.crema.CremaServiceProvider
import io.konaframework.http.HttpKernel
import io.konaframework.http.HttpServiceProvider
import io.konaframework.http.KonaHttpServer
import io.konaframework.http.Router
import io.konaframework.inertia.InertiaServiceProvider
import routes.WebRoutes

object App {
    @JvmStatic
    fun create(args: Array<String>): Application {
        val app = Kona.create(args)
            .configure { cfg ->
                cfg.set("inertia.root_view", "app")
                cfg.set("inertia.version", "1")
            }
            .providers(HttpServiceProvider(), CremaServiceProvider(), InertiaServiceProvider())
            .afterBoot { application ->
                application.resolve(HttpKernel::class.java).pushMiddleware(HandleInertia())
            }
            .boot()
        WebRoutes.register(app.resolve(Router::class.java))
        return app
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val app = create(args)
        val host = Env.getOrDefault("HOST", "127.0.0.1")
        val port = Env.getOrDefault("PORT", "4000").toInt()
        KonaHttpServer(app.resolve(HttpKernel::class.java), host, port).start()
    }
}
