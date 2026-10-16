package io.konaframework.inertia.assets

/**
 * Backward-compatible facade — implementation lives in `io.konaframework.crema.assets`
 * so Crema `@vite` and Inertia share one Vite hot/manifest resolver.
 */
typealias AssetManifest = io.konaframework.crema.assets.AssetManifest
typealias ViteException = io.konaframework.crema.assets.ViteException

object Vite {
    @JvmStatic
    fun setManifest(value: AssetManifest?) {
        io.konaframework.crema.assets.Vite.setManifest(value)
    }

    @JvmStatic
    fun manifest(): AssetManifest = io.konaframework.crema.assets.Vite.manifest()

    @JvmStatic
    fun reset() {
        io.konaframework.crema.assets.Vite.reset()
    }

    @JvmStatic
    fun tags(vararg entries: String): String = io.konaframework.crema.assets.Vite.tags(*entries)

    @JvmStatic
    fun asset(entry: String): String = io.konaframework.crema.assets.Vite.asset(entry)
}
