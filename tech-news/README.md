# TECH NEWS API DOCUMENTATION 📃

baseUrl: `http://10.234.90.77:8766/tech-news`


## NEWS: 📰

### POST news:
Endpoint: `/news`

Type: Multpartform

Attributes:

    title: String
    body: Text
    tags: List<String>
    image: file (optional)
    isPublished: boolean (optional)


### GET news preview:
Endpoint: `/news/preview`

Type: pathParameters

    sortBy: String (optional) ['view', 'latest' or 'relevance']
    title: String (optional)
    size: int (optional)
    page: int (optional)

e.g.: `/news/preview?sortBy=relevance&size=5&page=2`

Return:
```json
{ 
    "id": "UUID"
    "updateDate": "String"
    "title": "String"
    "author": "String" 
    "imageUrl": "url",
    "alreadyUpVoted": boolean
}
```

### GET news by ID:     
Endpoint: `/news/{uuid}`

Return:
```json
{
    "id": UUID
    "author": "String" 
    "creationDate": "String"
    "updateDate": "String"
    "title": "String"
    "body": "Text"
    "tags": List<String>
    "imageUrl": "url"
    "isPublished": boolean,
    "alreadyUpVoted": boolean
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
    body: Text (optional)
    tags: List<String> (optional)
    image: file (optional) 


### PATCH publish news:
Endpoint: `/news/{uuid}/publish`


### PATCH archive news: 
Endpoint: `/news/{uuid}/archive`

### PATCH upVote for news
Endpoint `/news/{uuid}/upvote`

Type: Header

    Authorization: Bearer token (azure)


## NEWS COMMENTS:

### POST comment:
Endpoint: `/comments/{newsId}`

Type: Json

Attributes: 

    comment: String


### GET comments:
Obs: always returning base on upVotes (relevance)

Endpoint: `/comments/{newsId}`

Return:
```json
{
    "id": long
    "newsId": "uuid"
    "author": "String"
    "publicationDate": "String"
    "comment": "String"
    "upVotes": int
    "alreadyUpVoted": boolean
}
```

### PATCH upVote for comments
Endpoint `/comments/{id}/upvote`

Type: Header

    Authorization: Bearer token (azure)


## NEWS BACKUP:

### GET news backup by ID:
Endpoint: `/news/{uuid}/backup`

Type: pathParameters

    level: int (1 to 3)
        
e.g.: `/news/ne7uhd-75h/backup?level=2`

Return: 
```json
{
    "id": long
    "newsId": "uuid"
    "title": "String"
    "body": "Text"
    "imageUrl": "url"
}
```
            
### PUT restore news from a backup:
Endpoint: `/news/{uuid}/backup/{backupId}/restore`

Return:
```json
{
    "id": UUID
    "author": "String" 
    "creationDate": "String"
    "updateDate": "String"
    "title": "String"
    "body": "Text"
    "tags": List<String>
    "imageUrl": "url"
    "isPublished": boolean
} 
```
    
