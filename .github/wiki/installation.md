You can just add the repository/dependecy for gradle or maven to your project

# Gradle

Repository (gradle):
```
repositories {
  maven("https://repo.derioo.de/releases")
}
```

Dependency (gradle):
```
dependencies {
  implementation("de.derioo.javautils:[subproject]:2.2.5")
}
```
Available subprojects:
- common
- paper

# Maven

Repository (maven):
```
<repositories>
  <repository>
    <id>derio-releases</id>
    <name>Derios Repository</name>
    <url>https://repo.derioo.de/releases</url>   
  </repository>
</repositories>
```

Dependency (maven):
```
<dependencies>
  <dependency>
    <groupId>de.derioo.javautils</groupId>
    <artifactId>[subproject]</artifactId>
    <version>2.1.1</version>
    </dependency>
</dependencies>
```

Available subprojects:
- common
- paper
