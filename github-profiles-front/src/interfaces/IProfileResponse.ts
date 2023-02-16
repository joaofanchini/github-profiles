export default interface IProfileResponse {
    id: number,
    login: string,
    avatarUrl: string,
    followers: any[],
    repositories: any[]
}