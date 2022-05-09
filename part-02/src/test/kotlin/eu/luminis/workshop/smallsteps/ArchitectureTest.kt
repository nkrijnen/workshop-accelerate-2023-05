package eu.luminis.workshop.smallsteps

import com.tngtech.archunit.base.DescribedPredicate.alwaysTrue
import com.tngtech.archunit.core.domain.JavaClass.Predicates.resideInAPackage
import com.tngtech.archunit.core.importer.ImportOption
import com.tngtech.archunit.junit.AnalyzeClasses
import com.tngtech.archunit.junit.ArchTest
import com.tngtech.archunit.library.Architectures.OnionArchitecture
import com.tngtech.archunit.library.Architectures.onionArchitecture

@AnalyzeClasses(
    packages = ["eu.luminis.workshop.smallsteps"],
    importOptions = [ImportOption.DoNotIncludeTests::class],
)
internal object ArchitectureTest {

    @ArchTest
    val onionArchitectureIsRespected: OnionArchitecture? = onionArchitecture()
        .domainModels("..logic..domainModel..")
        .domainServices("..logic..domainService..")
        .applicationServices("..logic..appService..")
        .withOptionalLayers(true)
        .adapter("api", "..api..")
        .adapter("persistence", "..persistence..")
        .ignoreDependency(
            resideInAPackage("eu.luminis.workshop.smallsteps"),
            alwaysTrue()
        )

}