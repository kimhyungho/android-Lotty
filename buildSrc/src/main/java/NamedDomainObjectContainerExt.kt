import org.gradle.api.Action
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.UnknownDomainObjectException

fun <T : Any> NamedDomainObjectContainer<T>.getOrCreate(name: String, configureAction: Action<T>) {
    try {
        this.getByName(name, configureAction)
    } catch (e: UnknownDomainObjectException) {
        this.create(name, configureAction)
    }
}