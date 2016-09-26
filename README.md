# Как подключить

## Шаг 1
```
allprojects {
    repositories {
        jcenter()
        maven {
            url "https://nexus.eastbanctech.ru/content/repositories/releases/"
        }
    }
}
```

## Шаг 2
Добавить сертификат сайта https://nexus.eastbanctech.ru/ в java кейстор

```
keytool -import -file <путь до сертификата сайта> -keystore <путь до кейстора>
```
Кейстор лежит по адресу %JAVA_HOME%/jre/lib/security/cacerts

Стандартный пароль к кейстору CHANGEIT

Лучше выполнять импорт с правами администратора

## Шаг 3

Добавить зависимость

```
compile 'etr.android.reamp:reamp:<last-version>'
```