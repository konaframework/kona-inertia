package bootstrap;

import app.http.middleware.HandleInertia;
import dev.kona.core.Application;
import dev.kona.core.Env;
import dev.kona.core.Kona;
import dev.kona.crema.CremaServiceProvider;
import dev.kona.http.HttpKernel;
import dev.kona.http.HttpServiceProvider;
import dev.kona.http.KonaHttpServer;
import dev.kona.http.Router;
import dev.kona.inertia.InertiaServiceProvider;
import routes.WebRoutes;

public final class App {
    private App() {}

    public static Application create(String[] args) {
        Application app = Kona.create(args)
            .configure(cfg -> {
                cfg.set("inertia.root_view", "app");
                cfg.set("inertia.version", "1");
            })
            .providers(new HttpServiceProvider(), new CremaServiceProvider(), new InertiaServiceProvider())
            .afterBoot(application ->
                application.resolve(HttpKernel.class).pushMiddleware(new HandleInertia())
            )
            .boot();
        WebRoutes.register(app.resolve(Router.class));
        return app;
    }

    public static void main(String[] args) {
        Application app = create(args);
        String host = Env.getOrDefault("HOST", "127.0.0.1");
        int port = Integer.parseInt(Env.getOrDefault("PORT", "4000"));
        new KonaHttpServer(app.resolve(HttpKernel.class), host, port).start();
    }
}
