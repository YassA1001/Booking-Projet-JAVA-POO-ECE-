import React from 'react';
import HebergementCard from './components/HebergementCard';

const mockHebergements = [
    {
        nom: "Villa Belle Vue",
        adresse: "123 Rue du Soleil, Marseille",
        prixParNuit: 120,
        nbChambres: 2,
        nbAdultes: 2,
        nbEnfants: 1,
        image: "https://source.unsplash.com/400x300/?villa"
    },
    {
        nom: "Studio Paris Centre",
        adresse: "75 Boulevard Haussmann, Paris",
        prixParNuit: 90,
        nbChambres: 1,
        nbAdultes: 1,
        nbEnfants: 0,
        image: "https://source.unsplash.com/400x300/?studio"
    },
    {
        nom: "Chalet Montagne",
        adresse: "Les Arcs, Savoie",
        prixParNuit: 150,
        nbChambres: 3,
        nbAdultes: 4,
        nbEnfants: 2,
        image: "https://source.unsplash.com/400x300/?chalet"
    }
];

const Home = () => {
    return (
        <div className="min-h-screen bg-gray-100 p-6">
            <h1 className="text-3xl font-bold text-center mb-8 text-gray-800">Nos Hébergements</h1>
            <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-6">
                {mockHebergements.map((h, index) => (
                    <HebergementCard key={index} hebergement={h} />
                ))}
            </div>
        </div>
    );
};

export default Home;
