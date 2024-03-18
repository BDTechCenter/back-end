TECH NEWS

baseUrl: http://10.234.90.77:8766/tech-news

NEWS:

    POST NEWS:
        endpoint: /news

        body: 
            type: multpartform
            attributes:
                author: String (temporarily)**
                title: String
                summary: String (need to review)**
                body: Text
                tags: List<String>
                image: file
                isPublished: boolean


    GET NEWS PREVIEW:
        endpoint: /news/preview

        pathParameters: 
            latest: boolean (optional)
            sortByView: boolean (optional)
            size: int (optional)
            page: int (optional)

            ex: /news/preview?latest=true&size=5&page=2
            
            obs: cant use latest and sortByView together
        
        return:
            id: UUID
            updateDate: String
            title: String
            author: String 
            imageUrl: url


    GET NEWS BY ID:     
        endpoint: /news/{uuid}

        return:
            id: UUID
            author: String 
            creationDate: String
            updateDate: String
            title: String
            summary: String (need to review)**
            body: Text
            tags: List<String>
            imageUrl: url
            isPublished: boolean


    GET NEWS BY TAG:    
        endpoint: /news

        pathParameters:
            tags: List<String> (mandatory)
            size: int (optional)
            page: int (optional)

            ex: /news?tags=docker,back-end

        return: 
            id: UUID
            updateDate: String
            title: String
            author: String 
            imageUrl: url


    GET ARCHIVED NEWS: (need to fix the return based on the author)**
        endpoint: /news/archived 

        return: 
            id: UUID
            updateDate: String
            title: String
            author: String 
            imageUrl: url


    PATCH UPDATE NEWS:  
        endpoint: /news/{uuid}/

        body:
            type: multpartform
            attributes:
                title: String (optional)
                summary: String (optional)
                body: Text (optional)
                tags: List<String> (optional)
                image: file (optional) 


    PATCH PUBLISH NEWS:
        endpoint: /news/{uuid}/publish


    PATCH ARCHIVE NEWS: 
        endpoint: /news/{uuid}/archive


NEWS BACKUP:

    GET NEWS BACKUP BY ID:
        endpoint: /news/{uuid}/backup

        pathParameters: 
            level: int (1 to 3)
        
        ex: /news/ne7uhd-75h/backup?level=2

        return: 
            id: long
            newsId: uuid
            title: String
            summary: String (need to review)**
            body: Text
            imageUrl: url

            
    PUT RESTORE NEWS FROM A BACKUP:
        endpoint: /news/{uuid}/backup/{backupId}/restore

        return:
            id: UUID
            author: String 
            creationDate: String
            updateDate: String
            title: String
            summary: String (need to review)**
            body: Text
            tags: List<String>
            imageUrl: url
            isPublished: boolean