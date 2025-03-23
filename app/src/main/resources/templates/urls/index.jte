@import hexlet.code.utils.NamedRoutes
@param hexlet.code.dto.urls.UrlsPage page 

@template.layout.page(
    content = @`
        @if(page.getFlash() != null)
            <p>${page.getFlash()}</p>
        @endif
        <table>
            <thead>
                <tr>
                    <th>Id</th>
                    <th>Имя</th>
                </tr>
            </thead>
            <tbody>
                @for(var url : page.getUrls())
                    <tr>
                        <td>${url.getId()}</td>
                        <td><a href="${NamedRoutes.urlPath(url.getId())}">${url.getName()}</a></td>
                    </tr>
                @endfor
            </tbody>
        </table>
    `
)