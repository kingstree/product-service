plugins {
    java
    id("org.springframework.boot") version "3.5.3"          // 안정판
    id("io.spring.dependency-management") version "1.1.5"   // 릴리스 버전
}

group = "com.bookshop"
version = "0.0.1-SNAPSHOT"
extra.set("testcontainersVersion", "1.19.8")

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}
dependencyManagement {// 책에서는 없으나... 클라우드 디펜던시가 필요했음
    imports {
        mavenBom("org.springframework.boot:spring-boot-dependencies:3.3.1")
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:2023.0.1")
        mavenBom("org.testcontainers:testcontainers-bom:${property("testcontainersVersion")}")

    }
}

configurations {//프로젝트 빌드 시 그래들이 설정 프로세서를 이용하도록 설정한다.
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.data:spring-data-commons")
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    //implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation ("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation ("org.springframework.cloud:spring-cloud-starter-config")
    implementation ("org.springframework.retry:spring-retry")
    implementation("org.flywaydb:flyway-core")

    compileOnly("org.projectlombok:lombok")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    runtimeOnly("org.postgresql:postgresql")
    // ⬇️ 추가: PostgreSQL 지원 모듈 (runtimeOnly 권장)
    runtimeOnly("org.flywaydb:flyway-database-postgresql") //Flyway 10.x부터는 flyway-core 만으로는 각 DB를 지원하지 않고, 별도 아티팩트를 추가해야 해요.
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation ("org.springframework.boot:spring-boot-starter-webflux")
    //testImplementation("org.springframework.security:spring-security-test")
    //testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation ("org.testcontainers:postgresql")
}



tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.named<org.springframework.boot.gradle.tasks.run.BootRun>("bootRun") {
    systemProperty("spring.profiles.active", "testdata")
}
tasks.bootBuildImage {
    builder.set("paketobuildpacks/builder-jammy-java-tiny:0.0.46")
   // imagePlatform.set("linux/arm64")
    imageName.set(project.name)
    imageName.set("ghcr.io/kingstree/product-service:latest")   // ★ 레지스트리·계정 포함
    environment.put("BP_JVM_VERSION", "17")

    docker {
        publishRegistry {
            username = project.findProperty("registryUsername") as String?
            password = project.findProperty("registryToken") as String?
            url = project.findProperty("registryUrl") as String?
        }
    }
}

testing {
    suites {
        val test by getting(JvmTestSuite::class) {
            useJUnitJupiter("5.10.2")   // 프레임워크와 버전을 한 번에 지정
        }
    }
}
