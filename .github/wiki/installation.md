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
  implementation("de.derioo:javautils:1.4.13")
}
```

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
    <groupId>de.derioo</groupId>
    <artifactId>javautils</artifactId>
    <version>1.4.13</version>
    </dependency>
</dependencies>
```
