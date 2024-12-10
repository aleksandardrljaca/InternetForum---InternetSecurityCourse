export class Properties{
    public static readonly API_BASE_URL='https://localhost:8000';
    public static readonly CLIENT_ID:string=<--YOUR CLIENT_ID-->;
    public static readonly GITHUB_CALLBACK:string='https://localhost:4200/api/oauth2-callback';
    public static readonly GITHUB_OAUTH_URL:string=`https://github.com/login/oauth/authorize?client_id=${this.CLIENT_ID}&redirect_uri=${this.GITHUB_CALLBACK}`;
}