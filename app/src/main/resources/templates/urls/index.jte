@import hexlet.code.utils.Routes
@param hexlet.code.dto.urls.UrlsPage page 

@template.layout.page(
    page = page,
    content = @`
        <div class="container-lg mt-5">
            <h1>Сайты</h1>
            <table class="table table-bordered table-hover mt-3">
                <thead>
                    <tr>
                        <th class="col-1">ID</th>
                        <th>Имя</th>
                        <th class="col-2">Последняя проверка</th>
                        <th class="col-1">Код ответа</th>
                    </tr>
                </thead>
                <tbody>
                @for(var entry : page.getUrls().entrySet())
                    <tr>
                        <td>${entry.getKey().getId()}</td>
                        <td><a href="${Routes.urlPath(entry.getKey().getId())}">${entry.getKey().getName()}</a></td>
                        <td>${entry.getValue().getCreatedAt()}</td>
                        <td>${entry.getValue().getStatusCode()}</td>
                    </tr>
                @endfor
                </tbody>
            </table>
        </div>
    `
)