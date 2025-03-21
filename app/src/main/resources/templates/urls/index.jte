@import hexlet.code.util.NamedRoutes
@param hexlet.code.dto.urls.UrlsPage page 

@template.layout.page(
    content = @`
        <table>
            <thead>
                <tr>
                    <th>ID</th>
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