export interface Post {
    code: number,
    date: Date,
    owner: string,
    text: string,
    image?: {
        code: number,
    }
    imageBase64?:string,
    comments?: Array<Comment>,
}

export interface SendPost {
    text: string,
    photoBase64?: string,
}

export interface SendComment {
    postCode:number,
    text: string,
}

export interface Comment {
    code: number,
    date: Date,
    post: number,
    owner: string,
    text: string,
}

export interface PhotoAlbum {
    code: number,
    name: string,
    owner: string,
    photoNumber:number,
    mainPhotoBase64?:string,
}

export interface Photo {
    code:number,
    date: Date,
    owner: string,
    photoBase64?:string,
}