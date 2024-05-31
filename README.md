Releases will be published here:

Repository (gradle):
```
repositories {
  maven("https://repo.derioo.de/releases")
}
```
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

Dependency (gradle):
```
dependencies {
  implementation("de.derioo:javautils:1.4.5")
}
```
Dependency (maven):
```
<dependencies>
  <dependency>
    <groupId>de.derioo</groupId>
    <artifactId>javautils</artifactId>
    <version>1.4.5</version>
    </dependency>
</dependencies>
```
