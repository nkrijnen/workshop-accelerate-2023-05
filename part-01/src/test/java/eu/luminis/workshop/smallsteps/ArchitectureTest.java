package eu.luminis.workshop.smallsteps;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.base.DescribedPredicate.alwaysTrue;
import static com.tngtech.archunit.core.domain.JavaClass.Predicates.resideInAPackage;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

@AnalyzeClasses(packages = "eu.luminis.workshop.smallsteps")
public class ArchitectureTest {

    @ArchTest
    static final ArchRule layer_dependencies_are_respected = layeredArchitecture()
            .layer("api").definedBy("eu.luminis.workshop.smallsteps.api..")
            .layer("logic").definedBy("eu.luminis.workshop.smallsteps.logic..")
            .layer("persistence").definedBy("eu.luminis.workshop.smallsteps.persistence..")
            .whereLayer("api").mayNotBeAccessedByAnyLayer()
            .whereLayer("logic").mayOnlyBeAccessedByLayers("api")
            .whereLayer("persistence").mayOnlyBeAccessedByLayers("logic")
            .ignoreDependency(
                    resideInAPackage("eu.luminis.workshop.smallsteps"),
                    alwaysTrue()
            );
}
