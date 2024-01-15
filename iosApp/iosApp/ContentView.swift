import SwiftUI
import shared
import KMPNativeCoroutinesAsync


extension DataStateNative {
    
}


class GridViewViewModel: ObservableObject {
    @Published var items: [Currency] = []
    
    func fetchData() async {
        let convertCurrency = ConvertCurrencyInjection().doConvert()
        let currency = Currency(code: "IDR", rate: 10000)
       
        
        convertCurrency.runNative(params: currency) { (result) -> () in
            switch result.state {
                case 1:
                    print("loading")
                case 2:
                    self.items =  result.result?.compactMap({ $0 as? Currency}) ?? []
                default:
                    print("error")
                
            }
        }
    }

    init() {
        Task {
            await fetchData()
        }
    }
}

struct ContentView: View {
    
    @StateObject var viewModel = GridViewViewModel()
    @State private var nominal: String = ""
    @State private var currency: String = ""
    
    let formatter: NumberFormatter = {
           let formatter = NumberFormatter()
           formatter.numberStyle = .decimal
           return formatter
       }()

    var body: some View {
        
        ScrollView {
            TextField("Input Nominal", value: $nominal, formatter: formatter)
                .padding(10)
            
            TextField("Currency", text: $currency)
                .padding(10)
            
            LazyVGrid(columns: [
                GridItem(.adaptive(minimum: 100)) // You can adjust the minimum width as needed
            ], spacing: 10) {
                ForEach(viewModel.items, id: \.self.rate) { item in
                        
                        ZStack {
                            Color(red: 0.8, green: 0.8, blue: 0.8) // Background color of the card
                                       .frame(height: 100)
                                       .cornerRadius(10)
                            VStack {
                                    Text(item.code)
                                       .font(.headline)
                                       .foregroundColor(.black)
                                       .padding(12)
                                
                                Text(String(item.rate))
                                               .font(.body)
                                               .padding(8)
                            }
                            
                        }
                        
                }
            }
            .padding()
        }
    }
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}
