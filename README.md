Releases will be published here:

Repository (gradle):
```
repositories {
  maven("https://nexus.derioo.de/releases")
}
```
Repository (maven):
```
<repositories>
  <repository>
    <id>derio-releases</id>
    <name>Derios Repository</name>
    <url>https://nexus.derioo.de/releases</url>
  </repository>
</repositories>
```
Dependency (gradle):
```
dependencies {
  implementation("de.derioo:javautils:[LATEST-VERSION]")
}
```
Dependency (maven):
```
<dependencies>
  <dependency>
    <groupId>de.derioo</groupId>
    <artifactId>javautils</artifactId>
    <version>[LATEST-VERSION]</version>
    </dependency>
</dependencies>
```
