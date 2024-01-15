//
//  CurrencyViewModel.swift
//  iosApp
//
//  Created by ariefmaffrudin on 19/10/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import shared



@MainActor
class CurrencyViewModel: ObservableObject {
    @Published var items: [String] = []
    
    func fetchData() async {
        let convertCurrency = ConvertCurrencyInjection().doConvert()
        let param = Currency(code: "IDR", rate: 10000.0)

//        do {
//            let stream = asyncSequence(convertCurrency.run(params: param))
//
//        } catch {
//
//        }
        
        
    }

   
}
