import React from 'react'

const HebergementCard = ({ hebergement }) => {
    return (
        <div className="bg-white shadow-md rounded-xl overflow-hidden hover:scale-[1.02] transition-all duration-200">
            <img
                src={hebergement.image || 'https://source.unsplash.com/400x300/?hotel'}
                alt={hebergement.nom}
                className="w-full h-48 object-cover"
            />
            <div className="p-4">
                <h2 className="text-xl font-semibold text-gray-800">{hebergement.nom}</h2>
                <p className="text-sm text-gray-500">{hebergement.adresse}</p>
                <div className="mt-2 flex justify-between items-center">
                    <span className="text-blue-600 font-bold">{hebergement.prixParNuit} €/nuit</span>
                    <span className="text-gray-600 text-sm">{hebergement.nbAdultes + hebergement.nbEnfants} pers · {hebergement.nbChambres} ch</span>
                </div>
            </div>
        </div>
    )
}

export default HebergementCard
