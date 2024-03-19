# TECH NEWS API DOCUMENTATION 📃

baseUrl: Ihttp://10.234.90.77:8766/tech-news`


## NEWS: 📰

### POST news:
Endpoint: `/news`

Type: Multpartform

Attributes:

    author: String (temporarily)**
    title: String
    summary: String (need to review)**
    body: Text
    tags: List<String>
    image: file
    isPublished: boolean


### GET news preview:
Endpoint: `/news/preview`

Type: pathParameters

    latest: boolean (optional)
    sortByView: boolean (optional)
    size: int (optional)
    page: int (optional)

e.g.: `/news/preview?latest=true&size=5&page=2`
    
Obs: can't use latest and sortByView together

Return:
```json
{ 
    "id": "UUID"
    "updateDate": "String"
    "title": "String"
    "author": "String" 
    "imageUrl": "url"
}
```

### GET news by ID:     
Endpoint: `/news/{uuid}`

Return:
```
{
    "id": UUID
    "author": "String" 
    "creationDate": "String"
    "updateDate": "String"
    "title": "String"
    "summary": "String" (need to review)**
    "body": "Text"
    "tags": List<String>
    "imageUrl": "url"
    "isPublished": boolean
}
```


### GET news by TAG:    
Endpoint: `/news`

Type: pathParameters
    
    tags: List<String> (mandatory)
    size: int (optional)
    page: int (optional)

e.g.: `/news?tags=docker,back-end`

Return:
```json
{
    "id": "UUID"
    "updateDate": "String"
    "title": "String"
    "author": "String" 
    "imageUrl": "url"
}
```


### GET archived news: 
Obs: Return based on the author hasn't been implemented yet**
    
Endpoint: `/news/archived`

Return: 
```json
{
    "id": "UUID"
    "updateDate": "String"
    "title": "String"
    "author": "String" 
    "imageUrl": "url"
}
```


### PATCH update news:  
Endpoint: `/news/{uuid}/`

Type: Multpartform
    
Attributes:

    title: String (optional)
    summary: String (optional)
    body: Text (optional)
    tags: List<String> (optional)
    image: file (optional) 


### PATCH publish news:
Endpoint: `/news/{uuid}/publish`


### PATCH archive news: 
Endpoint: `/news/{uuid}/archive`


## NEWS BACKUP:

### GET news backup by ID:
Endpoint: `/news/{uuid}/backup`

Type: pathParameters

    level: int (1 to 3)
        
e.g.: `/news/ne7uhd-75h/backup?level=2`

Return: 
```
{
    "id": long
    "newsId": "uuid"
    "title": "String"
    "summary": "String" (need to review)**
    "body": "Text"
    "imageUrl": "url"
}
```
            
### PUT restore news from a backup:
Endpoint: `/news/{uuid}/backup/{backupId}/restore`

Return:
```
{
    "id": UUID
    "author": "String" 
    "creationDate": "String"
    "updateDate": "String"
    "title": "String"
    "summary": "String" (need to review)**
    "body": "Text"
    "tags": List<String>
    "imageUrl": "url"
    "isPublished": boolean
} 
```
    
