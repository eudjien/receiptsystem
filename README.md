### Build & run

**CLI**

```
gradlew checksystem-cli:build
gradlew checksystem-cli:run --args="..."
```

### CLI arguments

- **-i=[deserialize/deserialize/database]**:
  этот аргумент всегда должен присутствовать, он отвечает за то что будет делать программа при её запуске. Доступные
  значения:
    - **deserialize-g:** сгенерировать чек из файла-генерата. При этом значении аргумента становятся доступны аргументы:
        - **-deserialize-g-format=[json]**, формат входящего файла
        - **-deserialize-g-path=[input file path]**, путь файла к файлу-генерату
    - **deserialize=[json/xml]:** загрузить чеки из файла. При этом значении должны присуствывать аргументы:
        - **-deserialize-path=[file_path]**, путь к файлу, который будет читаться.
        - **-deserialize-format=[json/xml]**, формат файла, который будет читаться.
    - **database:** загрузить предопределенные чеки из базы

___

- **-serialize=[true/false]**: этот агрумент говорит что все чеки которые были загружены или сгенерированы - будут
  записаны в файл, например другого формата, json->xml или наоборот. При этом аргументе должны присуствывать аргументы:
    - **-serialize-path=[file_path]:** путь к файлу для сохранения
    - **-serialize-format=[json/xml]:** формат файла для сохранения

---

- **-print=[true/false]**: этот аргумент говорит что все чеки которые были загружены или сгенерированы - будут
  записаны в файл в формате простого чека, например для покупателя (на эти чеки можно только смотреть, загружать обратно
  в программу нельзя). При этом аргументе должны присутствовать аргументы:
    - **-print-path=[file_path]:** путь к файлу для сохранения
    - **-print-format=[text/html/pdf]:** формат файла для сохранения
    - **-print-pdf-template=[true/false]:** использовать шаблон или нет (только pdf). При этом аргументе доступны
      аргументы:
        - **-print-print-template-path=[true/false]:** путь к файлу-шаблону
        - **-print-print-template-offset=[0-*]:** вертикальное смешение контента по отношению к шаблону

---

- **-input-filter-id=[id1,id2,id3]**: фильтрация чеков по Id, неважно откуда были загружены чеки, из файла или памяти
  этот аргумент доступен при **-i=[generate/deserialize/pre-defined]**

---

- **-proxied-services=[true/false]**: подключить dynamic proxy для логирования сервисов

---

### Примеры комманд:

**Сгенерировать чеки из файла-генерата и записать полную структуру чека в JSON файл:**

```
-i=deserialize-g 
-deserialize-g-format=json 
-desirialize-g-path="C:\checks\checks-generate.json" 
-serialize=true 
-serialize-format=json 
-serialize-path="C:\checks\checks.json"
```

**Загрузить все чеки из базы данных и сохранить их в JSON файл:**

```
-i=database
-serialize=true 
-serialize-format=json 
-serialize-path="C:\checks\checks.json"
```

**Загрузить все чеки из базы данных и сохранить их в XML файл:**

```
-i=database
-serialize=true 
-serialize-format=xml 
-serialize-path="C:\checks\checks.xml"
```

**Загрузить все чеки из базы данных и напечатать их в текстовый файл:**

```
-i=database
-print=true 
-print-format=text 
-print-path="C:\checks\checks_print.txt"
```

**Загрузить все чеки из базы данных и напечатать их в PDF файл:**

```
-i=database
-print=true 
-print-format=pdf 
-print-path="C:\checks\checks_print.pdf"
```

**Загрузить чеки из XML и напечатать их в HTML файл:**

```
-i=deserialize 
-deserialize-path="C:\checks\checks.xml" 
-deserialize-format=xml 
-serialize=true 
-print-format=text 
-print-path="C:\checks\checks.html"
```

**Загрузить чеки из базы данных, отфильтрованные по Id, и напечатать их в файл в текстовый файл:**

```
-i=database
-filter-id="1,2,3" 
-print=true 
-print-format=text 
-print-path="C:\checks\checks.txt"
```

**Загрузить чеки из JSON файла:**

```
-i=deserialize 
-deserialize-format=json
-deserialize-path="C:\checks\checks.json" 
```

**Загрузить чеки из JSON файла и сохранить их в файл в формате XML:**

```
-i=deserialize 
-deserialize-format=json 
-deserialize-path="C:\checks\checks.json" 
-serialize=true 
-serialize-format=xml 
-serialize-path="C:\checks\checks.xml"
```

