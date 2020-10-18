export interface Post {
    code: number,
    date: Date,
    owner: string,
    text: string,
    image?: {
        code: number,
    }
    imageBase64?,
}

export interface SendPost {
    text: string,
    photoBase64?: string,
}

export interface Comment {
    code: number,
    date: Date,
    post: number,
    owner: string,
    text: string,
}