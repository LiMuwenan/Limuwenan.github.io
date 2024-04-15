# 1 微服务介绍

# 2 gRPC介绍

`gRPC`是一款语言中立、平台中立，开源的远程调用系统，`gRPC`客户端和服务端可以在多种环境中运行和交互，例如`java`写一个服务器，可以用`go`语言卸客户端调用

数据在进行网络传输的时候，需要进行序列化，`gRPC`默认使用`protocaol buffers`

# 3 protobuf

protobuf 适合高性能，对响应速度有要求的数据传输出场景。因为 protobuf 是二进制数据格式，需要编码和解码，数据本身不具备可读性，因此只能反序列化后得到真正可读的数据。



## 3.1 安装

- 第一步：下载通用编译器

[github release版本](https://github.com/protocolbuffers/protobuf/releases)

根据操作系统和版本进行下载，解压出来是 `protoc.exe`

- 第二步：配置环境变量

配置到PATH中

- 第三步：安装go专用的protoc的生成器

通用编译器会调用对应语言的生成器

```
go install google.golang.org/protobuf/cmd/protoc-gen-go@v1.28
```

这个会自动下载到 `$GOPATH/bin` 中



## 3.2 Hello World

创建一个proto文件，`User`类包含`username`和`age`两个字段

```protobuf
// 指定语法版本
syntax = "proto3";
// option go_package = "path;name";
// path：表示生成go文件存放的地址，会地洞生成目录
// name： 表示生成go文件所属包名。不配置报名会按照proto文件的包名进行生成
option go_package = "../service;service";

message User {
  string username = 1;
  int32 age = 2;
}
```

然后用命令行进行代码生成

```bash
protoc --go_out=../service user.proto
// --go_out 后的路径文件夹必须存在。最终会在运行该命令的路径下将该命令指定路径和 option go_package下的路径进行拼接
```

**遇到的问题：**

生成的代码爆红，使用GoLand自动下载出错：

解决一：在设置中勾选 go->Go Modules->Enable Go modules integration

解决二：重新设置go env -w GOPROXY

测试生成代码使用

```go
package main

func main() {
	user := &service.User{ // 实现一个对象
		Username: "ligen",
		Age: 25,
	}

	//序列化
	marshal, err := proto.Marshal(user) // 转换为二进制
	if err != nil {
		panic(err)
	}

	//反序列化
	newUser := &service.User{}
	err = proto.Unmarshal(marshal, newUser)
	if err != nil {
		panic(err)
	}

	fmt.Print(newUser.String()) // 将先序列化，又反序列化的结果输出
}
```

## 3.3 proto文件介绍

### 3.3.1 message介绍

`message` 是定义一个消息类型的通用关键字

消息就是需要传输的数据格式的定义，类似于C++中的class，Java中的class，Go中的struct



### 3.3.2 字段规则

- `required`：必填字段(proto3中已经废弃)
- `optional`：可选字段
- `repeated`：可重复字段

```protobuf
message User {
  string username = 1;
  int32 age = 2;
  optional string email = 3;
  repeated string addresses = 4;
}
```

对应生成的struct

```go
type User struct {
	state         protoimpl.MessageState
	sizeCache     protoimpl.SizeCache
	unknownFields protoimpl.UnknownFields

	Username  string   `protobuf:"bytes,1,opt,name=username,proto3" json:"username,omitempty"`
	Age       int32    `protobuf:"varint,2,opt,name=age,proto3" json:"age,omitempty"`
	Email     *string  `protobuf:"bytes,3,opt,name=email,proto3,oneof" json:"email,omitempty"`
	Addresses []string `protobuf:"bytes,4,rep,name=addresses,proto3" json:"addresses,omitempty"`
}
```

### 3.3.3 字段映射

### 3.3.4 默认值

### 3.3.5 标识号

`标识号`：在消息体定义中，每个字段都有一个唯一的标识号

```protobuf
message User {
  string username = 1; // 位置1
  int32 age = 2;
  optional string email = 3;
  repeated string addresses = 4; // 位置4
}
```

### 3.3.6 定义多个消息类型

一个proto文件中定义多个消息

多个类

### 3.3.7 嵌套消息

嵌套类

### 3.3.8 定义服务（Service）

如果想要将消息类型用在RPC系统中，可以在.proto文件中定义一个RPC服务接口，protocol buffer 编译器将会根据所选择的不同语言生成服务接口代码及存根。

```protobuf
service SearchService {
	//rpc 服务的函数名 （传入参数） 返回 （返回参数）
	rpc Search (SearchRequest) returns (SearchResponse);
}
```

上面的定义表示：定义了一个RPC服务，该方法接收SearchRequest返回SearchResponse，这两个都是message



# 4 gRPC实例

## 4.1 RPC和gRPC

RPC（Remote Procedure Call）远程过程调用协议，一种通过网络从远程计算机上请求服务，而不需要了解底层网络技术的协议。RPC它假定某些协议的存在，例如TCP/UDP，为通信程序之间携带信息数据。

过程是什么？过程就是业务处理、计算任务

RPC采用客户端/服务端的模式，通过request-response消息模式实现

gRPC客户端应用可以像调用本地对象一样直接调用另一台不同机器上服务端应用的方法，是的能够更容易地创建分布式应用和服务。

### 4.1.1 HTTP2

## 4.2 实例

### 4.2.1 服务端

```protobuf
syntax = "proto3";

option go_package = "../service;service";

package service;

message ProductRequest {
  int32 prod_id = 1;
}

message ProductResponse {
  int32 prod_stock = 1;
}

// 定义服务主体
service ProdService {
  // 定义方法
  rpc GetProductStock(ProductRequest) returns(ProductResponse);
}
```

生成grpc服务地命令和普通服务不同

```
protoc --go_out=./ --go-grpc_out=./  product.proto
// 前面的--go_out生成类对象，后面--go-grpc_out生成gprc相关代码
// 没有--go-grpc_out不会有类对象，会报错
```

自己写一个服务端

```go
func main() {
	rpcServer := grpc.NewServer() // 用grpc包 new一个服务端

	service.RegisterProdServiceServer(rpcServer, service.ProductService) // 这个service是自己的包，注册一个服务打破rpcServer
	// 这个方法接口要注册一个对象，该对象对应的接口类（抽象类）在生成的rpc文件中，先要将该抽象类实现以下，也就是再写一个实体类，代码贴在下面
	listener, err := net.Listen("tcp", ":8002")
	if err != nil {
		log.Fatal("启动监听出错", err)
	}

	err = rpcServer.Serve(listener)
	if err != nil {
		log.Fatal("启动服务出错")
	}
}

```

```go
var ProductService = &productService{}

type productService struct { // go中只要满足了某个接口的方法集，就会自动实现该接口
    // 新版本实现结构体需要加
    // 包名.UnimplementedHelloServiceServer


}

func (p *productService) GetProductStock(ctx context.Context, request *ProductRequest) (*ProductResponse, error) {
	// 实现具体业务逻辑
	stock := p.GetStockById(request.ProdId)
	return &ProductResponse{ProdStock: stock}, nil
}

func (p *productService) GetStockById(id int32) int32 {
    fmt.Print("远程调用id:", id)
	return 100 // 返回库存
}

func (p *productService) mustEmbedUnimplementedProdServiceServer() {

}
```



### 4.2.2 客户端

在客户端代码所在文件夹（机器上），拷贝一份生成的protobuf和rpc代码（要告知客户端有哪些接口在远程可以调用）

```go
func main () {

	// 启动连接，无认证
	conn, err := grpc.Dial(":8002", grpc.WithTransportCredentials(insecure.NewCredentials())) // 第二个参数是带了一个证书

	if err != nil {
		log.Fatal("客户端连接出错", err)
	}

	// 调用product_grpc.pb.go 中的NewProdServiceClient方法
	productServiceClient := service.NewProdServiceClient(conn)

	request := &service.ProductRequest{
		ProdId: 123,
	}
	// 直接像调用本地方法一样调用
	stock, err := productServiceClient.GetProductStock(context.Background(), request)
	if err != nil {
		log.Fatal("查询库存出错", err)
	}
	fmt.Print("库存:", stock)
}
```

客户端主要分几个步骤：

1. 启动连接
2. 实例化客户端
3. 调用远程接口

# 5 认证

上面演示的步骤中，客户端和服务端连接并没有进行任何的加密和认证，谁人都能连接。接下来我们通过加入证书的方式，实现加密和认证，保证安全性。

TLS（Transport Layer Sercurity， 安全传输层），TLS是建立在传输层TCP之上的协议，服务于应用层，它的前身是SSL（Secure Socket Layer，安全套接字层），它实现了将应用层的报文进行加密后再交由TCP进行传输的功能。

TLS协议主要解决如下三个网络安全问题：

- 保密（message privacy），保密通过加密encryption实现，所有信息都加密传输，第三方无法嗅探
- 完整性（message integrity），通过MAC校验机制，一旦被篡改，通信双方会立刻发现
- 认证（mutual authentication），双方认证，双方都可以配备证书，防止身份被冒充

## 5.1 生成自签证书

生产环境可以购买证书或者使用一些平台发放的免费证书

- 安装openssl

[下载安装网址](http://slproweb.com/products/Win32OpenSSL.html)

配环境变量

- 生成私钥文件

执行下面的命令在当前文件夹生成私钥

```
openssl genrsa -des3 -out ca.key 2048
```

- 创建证书签名文件

以上一步生成的私钥，生成对应的证书签名文件

```
openssl req -new -key ca.key -out ca.csr
```

```
这一步需要输入一些信息
Country Name (2 letter code) [AU]:cn
State or Province Name (full name) [Some-State]:sx
Locality Name (eg, city) []:xa
Organization Name (eg, company) [Internet Widgits Pty Ltd]:ligen
Organizational Unit Name (eg, section) []:ligen
Common Name (e.g. server FQDN or YOUR name) []:ligen.com
Email Address []:bear.gen.li@outlook.com

Please enter the following 'extra' attributes
to be sent with your certificate request
A challenge password []:
An optional company name []:
```

- 生成正式的证书

```
openssl x509 -req -days 365 -in ca.csr -signkey ca.key -out ca.crt
```

上面的所有步骤生成好了证书，接下来配置openssl.cnf。生成一个go中常用的SAN证书

将openssl.cnf（位于安装openssl的目录）移动到项目的证书相关文件夹，并修改

1. 分别将 `req_extensions` 和 `copy_extensions` 前面的注释去掉
2. 添加字段

```
[ v3_req ]

subjectAltName = @alt_names
```

3. 添加新标签

```
# 因为该标签被v3_req引用，要写在它的上面
[ alt_names ]
# 要验证的域名
DNS.1 = *.ligen.com
DNS.2 = *.ligen.com.cn
```

4. 生成证书私钥server.key

```
openssl genpkey -algorithm RSA -out server.key
```

5. 通过私钥server.key生成证书请求文件server.csr

```
openssl req -new -nodes -key server.key -out server.csr -days 3650 -config ./openssl.cnf -extensions v3_req
```

6. 生成SAN证书，通过上面ca证书生成了新的证书

```
openssl x509 -req -days 365 -in server.csr -out server.pem -CA ca.crt -CAkey ca.key -CAcreateserial -extfile ./openssl.cnf -extensions v3_req
```

## 5.2 服务端应用证书

将`server.key`和`server.pem`拷贝到项目中，并用代码读取验证

和原来的代码相比多了2-6行，修改了第8行

```go
func main() {
	//添加证书
	creds, err2 := credentials.NewServerTLSFromFile("cert/server.pem", "cert/server.key") // 连接时没有证书就会报错
	if err2 != nil {
		log.Fatal("证书生成错误", err2)
	}

	rpcServer := grpc.NewServer(grpc.Creds(creds)) // 用grpc包 new一个服务端

	service.RegisterProdServiceServer(rpcServer, service.ProductService) // 这个service是自己的包，注册一个服务打破rpcServer

	listener, err := net.Listen("tcp", ":8002")
	if err != nil {
		log.Fatal("启动监听出错", err)
	}

	err = rpcServer.Serve(listener)
	if err != nil {
		log.Fatal("启动服务出错")
	}
}
```

## 5.3 客户端应用证书

和原来的代码相比多了2-6行，修改了第9行

```go
func main () {
	//添加证书
	creds, err2 := credentials.NewClientTLSFromFile("cert/server.pem", "*.ligen.com") // 连接时没有证书就会报错
	if err2 != nil {
		log.Fatal("证书生成错误", err2)
	}

	// 启动连接
	conn, err := grpc.Dial(":8002", grpc.WithTransportCredentials(creds)) // 第二个参数是带了一个证书

	if err != nil {
		log.Fatal("客户端连接出错", err)
	}

	// 调用product_grpc.pb.go 中的NewProdServiceClient方法
	productServiceClient := service.NewProdServiceClient(conn)

	request := &service.ProductRequest{
		ProdId: 123,
	}
	// 直接像调用本地方法一样调用
	stock, err := productServiceClient.GetProductStock(context.Background(), request)
	if err != nil {
		log.Fatal("查询库存出错", err)
	}
	fmt.Print("库存:", stock)
}
```

## 5.4 双向认证

上面属于一个单向认证的过程，只有服务端生成了对应的公钥和私钥。如果是双向认证，客户端也需要生成一对公钥私钥

1. 生成客户端私钥

```
openssl genpkey -algorithm RSA -out client.key
```

2. 生成证书

```
openssl req -new -nodes -key client.key -out client.csr -days 3650 -config ./openssl.cnf -extensions v3_req
```

3. SAN证书

```
openssl x509 -req -days 365 -in client.csr -out client.pem -CA ca.crt -CAkey ca.key -CAcreateserial -extfile ./openssl.cnf -extensions v3_req
```

服务端

```go
func main() {
	//双向验证证书
	cert, err := tls.LoadX509KeyPair("cert/server.pem", "cert/server.key")
	if err != nil {
		log.Fatal("证书生成错误", err)
	}
	// 创建一个新的，空的 CertPool
	certPool := x509.NewCertPool()
	ca, err := ioutil.ReadFile("cert/ca.crt")
	if err != nil {
		log.Fatal("ca证书读取错误", err)
	}
	// 尝试解析所传入的 PEM 编码的证书。如果解析成功会将其加到 CertPool 中，便于后面的使用
	certPool.AppendCertsFromPEM(ca)
	// 构建基于 TLS 的 TransportCredentials 选项
	creds := credentials.NewTLS(&tls.Config{
		// 设置证书链，允许包含一个或多个
		Certificates: []tls.Certificate{cert},
		// 要求必须校验客户端的证书，可以根据实际情况选用以下参数
		ClientAuth: tls.RequireAndVerifyClientCert,
		// 设置根证书的集合，校验方式使用ClientAuth中设定的模式
		ClientCAs: certPool,
	})

	rpcServer := grpc.NewServer(grpc.Creds(creds)) // 用grpc包 new一个服务端
    
    // ...
}
```

客户端

```go
func main () {
	//双向验证证书
	// 从证书相关文件中读取和解析信息，得到证书公钥、密钥对
	cert, _ := tls.LoadX509KeyPair("cert/client.pem","cert/client.key")
	// 创建一个新的、空的CertPool
	certPool := x509.NewCertPool()
	ca, err := ioutil.ReadFile("cert/ca.crt")
	if err != nil {
		log.Fatal("ca证书读取错误", err)
	}
	// 尝试解析所传入的 PEM 编码的证书。如果解析成功会将其加到 CertPool 中，便于后面的使用
	certPool.AppendCertsFromPEM(ca)
	// 构建基于 TLS 的 TransportCredentials 选项
	creds := credentials.NewTLS(&tls.Config{
		// 设置证书链，允许包含一个或多个
		Certificates: []tls.Certificate{cert},
		// 要求必须校验客户端的证书，可以根据实际情况选用以下参数
		ServerName: "*.ligen.com",
		RootCAs: certPool,
	})

	// 启动连接
	conn, err := grpc.Dial(":8002", grpc.WithTransportCredentials(creds)) // 第二个参数是带了一个证书
	// ...
}
```

## 5.5 Token认证

### 5.5.1 服务端添加用户名密码的校验

服务端token

```go
func main() {

	//双向验证证书
	cert, err := tls.LoadX509KeyPair("cert/server.pem", "cert/server.key")
	if err != nil {
		log.Fatal("证书生成错误", err)
	}
	// 创建一个新的，空的 CertPool
	certPool := x509.NewCertPool()
	ca, err := ioutil.ReadFile("cert/ca.crt")
	if err != nil {
		log.Fatal("ca证书读取错误", err)
	}
	// 尝试解析所传入的 PEM 编码的证书。如果解析成功会将其加到 CertPool 中，便于后面的使用
	certPool.AppendCertsFromPEM(ca)
	// 构建基于 TLS 的 TransportCredentials 选项
	creds := credentials.NewTLS(&tls.Config{
		// 设置证书链，允许包含一个或多个
		Certificates: []tls.Certificate{cert},
		// 要求必须校验客户端的证书，可以根据实际情况选用以下参数
		ClientAuth: tls.RequireAndVerifyClientCert,
		// 设置根证书的集合，校验方式使用ClientAuth中设定的模式
		ClientCAs: certPool,
	})

	// 实现token认证，需要合法的用户名和密码
	// 实现一个拦截器
	var authInterceptor grpc.UnaryServerInterceptor
	authInterceptor = func(
			ctx context.Context,
			req interface{},
			info *grpc.UnaryServerInfo,
			handler grpc.UnaryHandler,
		) (resp interface{}, err error) {
		// 拦截普通方法请求， 验证Token
		err = Auth(ctx)
		if err != nil {
			return
		}
		// 继续处理请求
		return handler(ctx, req)
	}

	rpcServer := grpc.NewServer(grpc.Creds(creds), grpc.UnaryInterceptor(authInterceptor)) // 用grpc包 new一个服务端

	service.RegisterProdServiceServer(rpcServer, service.ProductService) // 这个service是自己的包，注册一个服务打破rpcServer

	listener, err := net.Listen("tcp", ":8002")
	if err != nil {
		log.Fatal("启动监听出错", err)
	}

	err = rpcServer.Serve(listener)
	if err != nil {
		log.Fatal("启动服务出错")
	}
}

func Auth(ctx context.Context) error {
	// 实际上就是获取到context中的用户名和密码用来比较
	md, ok := metadata.FromIncomingContext(ctx)
	if !ok {
		return fmt.Errorf("missing credentials")
	}
	var user string
	var password string
	if val, ok := md["user"]; ok {
		user = val[0]
	}

	if val, ok := md["password"]; ok {
		password = val[0]
	}

	if user != "admin" || password != "admin" {
		return status.Errorf(codes.Unauthenticated, "token不合法")
	}

	return nil
}
```

客户端

客户端需要实现两个接口

- `GetRequestMetadata` ：方法返回认证需要的必要信息
- `RequireTransportSecurity` ：方法表示是否启用安全链接，在生产环境中，一般都是启用的。这里测试不启用

```go
func main () {
	//双向验证证书
	// 从证书相关文件中读取和解析信息，得到证书公钥、密钥对
	cert, _ := tls.LoadX509KeyPair("cert/client.pem","cert/client.key")
	// 创建一个新的、空的CertPool
	certPool := x509.NewCertPool()
	ca, err := ioutil.ReadFile("cert/ca.crt")
	if err != nil {
		log.Fatal("ca证书读取错误", err)
	}
	// 尝试解析所传入的 PEM 编码的证书。如果解析成功会将其加到 CertPool 中，便于后面的使用
	certPool.AppendCertsFromPEM(ca)
	// 构建基于 TLS 的 TransportCredentials 选项
	creds := credentials.NewTLS(&tls.Config{
		// 设置证书链，允许包含一个或多个
		Certificates: []tls.Certificate{cert},
		// 要求必须校验客户端的证书，可以根据实际情况选用以下参数
		ServerName: "*.ligen.com",
		RootCAs: certPool,
	})

	token := &auth.Authentication{ // 需要传入的用户名和密码
		User: "admin",
		Password: "admin",
	}
	// 启动连接
	conn, err := grpc.Dial(":8002", grpc.WithTransportCredentials(creds), grpc.WithPerRPCCredentials(token)) // 第二个参数是带了一个证书

	if err != nil {
		log.Fatal("客户端连接出错", err)
	}

	// 调用product_grpc.pb.go 中的NewProdServiceClient方法
	productServiceClient := service.NewProdServiceClient(conn)

	request := &service.ProductRequest{
		ProdId: 123,
	}
	// 直接像调用本地方法一样调用
	stock, err := productServiceClient.GetProductStock(context.Background(), request)
	if err != nil {
		log.Fatal("查询库存出错", err)
	}
	fmt.Print("库存:", stock)
}

// 该类要实现下面两个接口
type Authentication struct {
	User string
	Password string
}

// 这两个方法是为了实现接口
func (a *Authentication) GetRequestMetadata(context.Context, ...string) (
		map[string]string, error,
	){
	return map[string]string{"user":a.User,"password":a.Password}, nil
}

func (a *Authentication) RequireTransportSecurity() bool {
	return false
}
```

# 6 import

在一个proto文件中引用另一个proto文件

```
syntax = "proto3";

// import 导入文件路径的根目录是 执行 protoc 命令所在的目录。
// 所以 import 要么写绝对路径，要么在相对路径时一定要写准确
import "/";

option go_package = "../service;service";
```

# 7 任意类型

`Any` 类型

需要导入包

```protobuf
import "google/protobuf/any.proto";
```

# 8 Stream

## 8.1 定义

```
// 普通rpc
rpc SimplePing(PingRequest) returns (PingReply)

// 客户端流rpc
rpc ClientStreamPing(stream PingRequest) returns (PingReply)

// 服务器端流rpc
rpc ServerStreamPing(PingRequest) returns (stream PingReply)

// 双向流rpc
rpc BothStreamPing(stream PingRequest) returns (stream PingReply)
```

